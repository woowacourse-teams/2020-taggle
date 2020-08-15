<template>
  <v-btn icon @click.prevent="openModal">
    <v-icon>mdi-plus</v-icon>
    <v-dialog v-model="dialog" max-width="500">
      <v-card>
        <v-app-bar dense flat>
          <v-toolbar-title>북마크 추가</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon @click="closeModal">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-app-bar>
        <v-card-text>
          <v-row>
            <v-col cols="10">
              <v-text-field
                v-model="createBookmarkRequest.url"
                label="URL 입력후 enter로저장 https://..."
                :rules="rules"
                @keypress.enter="addBookmark"
              ></v-text-field>
              <vue-tags-input
                v-model="tag"
                :tags="tags"
                @before-adding-tag="onAddTagBookmark"
                @before-deleting-tag="onDeleteTagBookmark"
                @tags-changed="(newTags) => (tags = newTags)"
                placeholder="해당 북마크에 태그를 추가하거나 삭제할 수 있습니다."
              />
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-btn>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import VueTagsInput from '@johmun/vue-tags-input';
import TagService from '@/api/module/tag.js';
import BookmarkService from '@/api/module/bookmark.js';
import { FETCH_CATEGORIES, FETCH_BOOKMARK_WITH_TAGS } from '@/store/share/actionTypes.js';
import { GET_TAG_ID_BY_NAME } from '@/store/share/getterTypes.js';

export default {
  name: 'BookmarkAddModal',
  components: {
    VueTagsInput,
  },
  data() {
    return {
      createBookmarkRequest: {
        url: '',
      },
      tagCreateRequest: {
        name: '',
      },
      dialog: false,
      tag: '',
      tags: [],
      rules: [(value) => !!value || '북마크로 저장할 URL값이 필요합니다.'],
      bookmarkId: '',
    };
  },
  computed: {
    ...mapGetters([GET_TAG_ID_BY_NAME]),
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES, FETCH_BOOKMARK_WITH_TAGS]),
    ...mapMutations([SHOW_SNACKBAR]),
    closeModal() {
      this.dialog = false;
    },
    openModal() {
      this.dialog = true;
    },
    async fetchBookmark(bookmarkId) {
      await this[FETCH_BOOKMARK_WITH_TAGS](bookmarkId);
    },
    async addBookmark() {
      try {
        this.bookmarkId = await BookmarkService.post(this.createBookmarkRequest);
        await this.fetchBookmark(this.bookmarkId);
        this[SHOW_SNACKBAR]('북마크 생성을 성공했습니다.');
      } catch (e) {
        this[SHOW_SNACKBAR]('북마크 생성중 오류가 발생했습니다.');
      }
      this.createBookmarkRequest.url = '';
    },
    async onAddTagBookmark(data) {
      this.tagCreateRequest.name = data.tag.text;
      try {
        const tagId = await TagService.create(this.tagCreateRequest);
        await TagService.addBookmarkOnTag(tagId, this.bookmarkId);
        await this[FETCH_CATEGORIES]();
        await this.fetchBookmark(this.bookmarkId);
        this[SHOW_SNACKBAR]('태그 북마크 생성을 성공했습니다.');
        data.addTag();
      } catch (e) {
        this[SHOW_SNACKBAR]('태그 북마크 생성중 오류가 발생했습니다.');
      }
    },
    async onDeleteTagBookmark(data) {
      const deleteName = data.tag.text;
      const tagId = this[GET_TAG_ID_BY_NAME](deleteName);
      try {
        await TagService.deleteBookmarkOnTag(tagId, this.bookmarkId);
        await this.fetchBookmark(this.bookmarkId);
        this[SHOW_SNACKBAR]('태그 북마크 삭제를 성공했습니다.');
        data.deleteTag();
      } catch (e) {
        this[SHOW_SNACKBAR]('태그 북마크 삭제중 오류가 발생했습니다.');
      }
    },
  },
};
</script>
