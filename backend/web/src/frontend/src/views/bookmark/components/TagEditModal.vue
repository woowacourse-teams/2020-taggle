<template>
  <v-btn icon @click.prevent="openModal">
    <v-icon>mdi-pencil</v-icon>
    <v-dialog v-model="dialog" max-width="500">
      <v-card>
        <v-app-bar dense flat>
          <v-toolbar-title>태그 편집</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon @click="closeModal">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-app-bar>
        <v-card-text>
          <vue-tags-input
            v-model="tag"
            :tags="tags"
            @tags-changed="(newTags) => (tags = newTags)"
            @before-adding-tag="onAddTagBookmark"
            @before-deleting-tag="onDeleteTagBookmark"
            placeholder="해당 북마크에 태그를 추가하거나 삭제할 수 있습니다."
          />
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-btn>
</template>

<script>
import VueTagsInput from '@johmun/vue-tags-input';
import { mapActions, mapGetters, mapMutations } from 'vuex';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import TagService from '@/api/module/tag.js';
import { BOOKMARK_TAGS, GET_TAG_ID_BY_NAME } from '@/store/share/getterTypes.js';
import { FETCH_BOOKMARK_WITH_TAGS, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';

export default {
  name: 'TagEditModal',
  components: {
    VueTagsInput,
  },
  props: {
    bookmark: {
      type: Object,
      required: true,
    },
  },
  computed: {
    ...mapGetters([BOOKMARK_TAGS, GET_TAG_ID_BY_NAME]),
  },
  data() {
    return {
      tagCreateRequest: {
        name: '',
      },
      dialog: false,
      tag: '',
      tags: [],
    };
  },
  watch: {
    dialog(newValue) {
      if (newValue) {
        this.fetchBookmarks();
      }
    },
  },
  methods: {
    ...mapActions([FETCH_BOOKMARK_WITH_TAGS, FETCH_CATEGORIES]),
    ...mapMutations([SHOW_SNACKBAR]),
    closeModal() {
      this.dialog = false;
    },
    openModal() {
      this.dialog = true;
    },
    async fetchBookmarks() {
      await this[FETCH_BOOKMARK_WITH_TAGS](this.bookmark.id);
      this.initTags();
    },
    async onAddTagBookmark(data) {
      this.tagCreateRequest.name = data.tag.text;
      try {
        const tagId = await TagService.create(this.tagCreateRequest);
        await TagService.addBookmarkOnTag(tagId, this.bookmark.id);
        await this[FETCH_BOOKMARK_WITH_TAGS](this.bookmark.id);
        await this[FETCH_CATEGORIES]();
        this[SHOW_SNACKBAR]('태그 북마크 생성이 성공적으로 이뤄졌습니다.');
        data.addTag();
      } catch (e) {
        this[SHOW_SNACKBAR]('태그 북마크 생성중 오류가 발생했습니다.');
      }
    },
    async onDeleteTagBookmark(data) {
      const deleteName = data.tag.text;
      const tagId = this[GET_TAG_ID_BY_NAME](deleteName);
      try {
        await TagService.deleteBookmarkOnTag(tagId, this.bookmark.id);
        await this[FETCH_BOOKMARK_WITH_TAGS](this.bookmarkId);
        this[SHOW_SNACKBAR]('태그 북마크 삭제가 성공적으로 이뤄졌습니다.');
        data.deleteTag();
      } catch (e) {
        this[SHOW_SNACKBAR]('태그 북마크 삭제중 오류가 발생했습니다.');
      }
    },
    initTags() {
      this.tags = this[BOOKMARK_TAGS].map((tag) => this.mapTag(tag));
    },
    mapTag(tagValue) {
      return {
        text: tagValue.name,
        tiClasses: ['ti-valid'],
      };
    },
  },
};
</script>
