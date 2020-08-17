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
import { mapMutations } from 'vuex';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import VueTagsInput from '@johmun/vue-tags-input';
import TagService from '@/api/module/tag.js';
import BookmarkService from '@/api/module/bookmark.js';

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
  methods: {
    ...mapMutations([SHOW_SNACKBAR]),
    closeModal() {
      this.dialog = false;
    },
    openModal() {
      this.dialog = true;
    },
    async addBookmark() {
      try {
        this.bookmarkId = await BookmarkService.post(this.createBookmarkRequest);
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
        // await this[FETCH_TAG_BOOKMARK](this.bookmarkId);
        data.addTag();
      } catch (e) {
        this[SHOW_SNACKBAR]('태그 추가중 오류가 발생했습니다.');
      }
    },
    // async onRemoveTagBookmark(data) {
    //   const deleteName = data.tag.text;
    //   const tagId = this[TAG_ID_BY_NAME](deleteName);
    //   try {
    //     await this[DELETE_TAG_BOOKMARK]({
    //       bookmarkId: this[BOOKMARK_ID],
    //       tagId,
    //     });
    //     await this[FETCH_TAG_BOOKMARK](this[BOOKMARK_ID]);
    //     data.deleteTag();
    //   } catch (e) {
    //     this[SHOW_SNACKBAR]('태그 북마크 삭제중 오류가 발생했습니다.');
    //   }
    // },
  },
};
</script>
