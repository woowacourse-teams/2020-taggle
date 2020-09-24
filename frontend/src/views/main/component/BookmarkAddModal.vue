<template>
  <v-btn dark large fixed bottom right fab @click.prevent="openModal" class="ma-4">
    <v-icon>mdi-plus</v-icon>
    <v-dialog v-model="dialog" max-width="500">
      <v-card>
        <v-app-bar dense flat>
          <v-toolbar-title>북마크 추가</v-toolbar-title>
          <v-spacer />
          <v-btn icon @click="closeModal">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-app-bar>
        <v-card-text class="pa-4">
          <v-form ref="bookmarkForm" @submit.prevent="addBookmark">
            <v-text-field
              v-model="url"
              label="주소를 입력하세요."
              :rules="rules.bookmark"
              :disabled="isBookmarkAdded"
            ></v-text-field>
          </v-form>
          <v-form ref="tagForm">
            <v-combobox
              v-model="tags"
              v-if="isBookmarkAdded"
              label="북마크를 입력하세요."
              :rules="rules.tag"
              small-chips
              multiple
              outlined
              hide-details
            >
              <template v-slot:selection="{ attrs, item, select, selected }">
                <v-chip
                  v-bind="attrs"
                  color="light-blue lighten-1"
                  text-color="white"
                  :input-value="selected"
                  @click="select"
                  close
                  small
                  @click:close="onDeleteTagBookmark(item)"
                >
                  <strong>{{ item }}</strong>
                </v-chip>
              </template>
            </v-combobox>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-btn>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex';
import { RESET_BOOKMARK_WITH_TAGS, SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import {
  CREATE_TAG_BOOKMARK,
  DELETE_TAG_BOOKMARK,
  FETCH_BOOKMARK_DETAIL,
  FETCH_CATEGORIES,
  CREATE_TAG,
  CREATE_BOOKMARK,
} from '@/store/share/actionTypes.js';
import { GET_TAG_ID_BY_NAME, BOOKMARK_WITH_TAGS } from '@/store/share/getterTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import validator from '@/utils/validator.js';

export default {
  name: 'BookmarkAddModal',
  data() {
    return {
      dialog: false,
      isTagsLoaded: false,
      tags: [],
      rules: {
        bookmark: validator.bookmark.url,
        tag: validator.tag.name,
      },
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
    tags(newTags, oldTags) {
      this.$refs.tagForm.resetValidation();
      if (!this.isTagsLoaded) {
        this.isTagsLoaded = true;
        return;
      }
      if (newTags.length > oldTags.length) {
        this.onAddTagBookmark(newTags[newTags.length - 1]);
      }
      if (newTags.length < oldTags.length) {
        const removeTagName = oldTags.filter((tag) => !newTags.includes(tag))[0];
        this.onDeleteTagBookmark(removeTagName);
      }
    },
  },
  computed: {
    ...mapGetters([GET_TAG_ID_BY_NAME, BOOKMARK_WITH_TAGS]),
    isBookmarkAdded() {
      return !!this.bookmarkId;
    },
  },
  methods: {
    ...mapActions([
      FETCH_CATEGORIES,
      FETCH_BOOKMARK_DETAIL,
      CREATE_TAG_BOOKMARK,
      DELETE_TAG_BOOKMARK,
      CREATE_TAG,
      CREATE_BOOKMARK,
    ]),
    ...mapMutations([SHOW_SNACKBAR, RESET_BOOKMARK_WITH_TAGS]),
    openModal() {
      this.dialog = true;
    },
    closeModal() {
      this.dialog = false;
    },
    clearInputTagForm() {
      this.tags = [];
      this.bookmarkId = '';
      this.url = '';
      this.isTagsLoaded = false;
      this[RESET_BOOKMARK_WITH_TAGS]();
      this.$refs.bookmarkForm.resetValidation();
      this.$refs.tagForm.resetValidation();
    },
    async addBookmark() {
      if (!this.$refs.bookmarkForm.validate()) {
        return;
      }
      try {
        this.isTagsLoaded = false;
        this.bookmarkId = await this[CREATE_BOOKMARK]({ url: this.url });
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmarkId });
        this.tags = this[BOOKMARK_WITH_TAGS].map((tag) => tag.name);
        this[SHOW_SNACKBAR](MESSAGES.BOOKMARK.ADD.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.BOOKMARK.ADD.FAIL);
      }
    },
    async onAddTagBookmark(targetTagName) {
      if (this.tags.length > 10 || !this.$refs.tagForm.validate()) {
        this.tags.splice(this.tags.indexOf(targetTagName), 1);
        return;
      }
      try {
        const tagId = await this[CREATE_TAG]({ name: targetTagName });
        await this[CREATE_TAG_BOOKMARK]({ tagId, bookmarkId: this.bookmarkId });
        await this[FETCH_CATEGORIES]();
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmarkId });
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.ADD.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.ADD.FAIL);
      }
    },
    async onDeleteTagBookmark(targetTagName) {
      try {
        const tagId = this[GET_TAG_ID_BY_NAME](targetTagName);
        await this[DELETE_TAG_BOOKMARK]({ tagId, bookmarkId: this.bookmarkId });
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmarkId });
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.SUCCESS);
        if (this.tags.indexOf(targetTagName) !== -1) {
          this.tags.splice(this.tags.indexOf(targetTagName), 1);
        }
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.FAIL);
      }
    },
  },
};
</script>
