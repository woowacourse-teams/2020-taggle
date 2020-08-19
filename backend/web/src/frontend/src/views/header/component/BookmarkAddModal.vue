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
              <v-form ref="bookmarkForm" @submit.prevent>
                <v-text-field
                  v-model="url"
                  label="URL 입력후 enter를 입력하여 저장. https://..."
                  :rules="rules"
                  :disabled="isBookmarkAdded"
                  @keypress.enter="addBookmark"
                ></v-text-field>
              </v-form>
              <vue-tags-input
                v-model="tag"
                v-if="isBookmarkAdded"
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
import { RESET_BOOKMARK_WITH_TAGS, SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import VueTagsInput from '@johmun/vue-tags-input';
import TagService from '@/api/module/tag.js';
import BookmarkService from '@/api/module/bookmark.js';
import {
  ADD_TAG_ON_BOOKMARK,
  DELETE_TAG_ON_BOOKMARK,
  FETCH_BOOKMARK_WITH_TAGS,
  FETCH_CATEGORIES,
} from '@/store/share/actionTypes.js';
import { GET_TAG_ID_BY_NAME } from '@/store/share/getterTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import validator from '@/utils/validator.js';

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
      rules: validator.bookmark.url,
      bookmarkId: '',
      url: '',
    };
  },
  watch: {
    dialog() {
      if (!this.dialog) {
        this.clearInputTagForm();
      }
    },
  },
  computed: {
    ...mapGetters([GET_TAG_ID_BY_NAME]),
    isBookmarkAdded() {
      return !!this.bookmarkId;
    },
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES, FETCH_BOOKMARK_WITH_TAGS, ADD_TAG_ON_BOOKMARK, DELETE_TAG_ON_BOOKMARK]),
    ...mapMutations([SHOW_SNACKBAR, RESET_BOOKMARK_WITH_TAGS]),
    closeModal() {
      this.dialog = false;
    },
    openModal() {
      this.dialog = true;
    },
    clearInputTagForm() {
      this.tag = '';
      this.tags = [];
      this.bookmarkId = '';
      this.url = '';
      this[RESET_BOOKMARK_WITH_TAGS]();
      this.$refs.bookmarkForm.resetValidation();
    },
    async addBookmark() {
      if (!this.$refs.bookmarkForm.validate()) {
        return;
      }
      try {
        this.bookmarkId = await BookmarkService.post({ url: this.url });
        await this.fetchBookmark();
        this[SHOW_SNACKBAR](MESSAGES.BOOKMARK.ADD.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.BOOKMARK.ADD.FAIL);
      }
    },
    async onAddTagBookmark(data) {
      const targetTagName = data.tag.text;
      try {
        const targetTagId = await TagService.create({ name: targetTagName });
        await this[ADD_TAG_ON_BOOKMARK]({ tagId: targetTagId, bookmarkId: this.bookmarkId });
        await this[FETCH_CATEGORIES]();
        await this.fetchBookmark();
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.ADD.SUCCESS);
        data.addTag();
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.ADD.FAIL);
      }
    },
    async onDeleteTagBookmark(data) {
      const targetTagName = data.tag.text;
      const targetTagId = this[GET_TAG_ID_BY_NAME](targetTagName);
      try {
        await this[DELETE_TAG_ON_BOOKMARK]({ tagId: targetTagId, bookmarkId: this.bookmarkId });
        await this.fetchBookmark();
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.SUCCESS);
        data.deleteTag();
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.FAIL);
      }
    },
    async fetchBookmark() {
      try {
        await this[FETCH_BOOKMARK_WITH_TAGS]({ bookmarkId: this.bookmarkId });
      } catch (e) {
        throw new Error(MESSAGES.BOOKMARK_WITH_TAGS.FETCH.FAIL);
      }
    },
  },
};
</script>
