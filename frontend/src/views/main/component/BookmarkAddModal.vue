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
              label="URL 입력후 enter를 입력하여 저장. https://..."
              :rules="rules.bookmark"
              :disabled="isBookmarkAdded"
            ></v-text-field>
          </v-form>
          <v-form ref="tagForm">
            <v-combobox
              v-model="tags"
              v-if="isBookmarkAdded"
              label="해당 북마크에 태그를 추가하거나 삭제할 수 있습니다."
              :rules="rules.tag"
              chips
              multiple
              solo
              flat
              outlined
            >
              <template v-slot:selection="{ attrs, item, select, selected }">
                <v-chip
                  v-bind="attrs"
                  color="light-blue lighten-1"
                  text-color="white"
                  :input-value="selected"
                  @click="select"
                  close
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
import { GET_TAG_ID_BY_NAME } from '@/store/share/getterTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import validator from '@/utils/validator.js';

export default {
  name: 'BookmarkAddModal',
  data() {
    return {
      dialog: false,
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
      if (newTags.length > oldTags.length) {
        this.onAddTagBookmark(newTags[newTags.length - 1]);
      }
      if (newTags.length < oldTags.length) {
        this.onDeleteTagBookmark(oldTags[oldTags.length - 1]);
      }
    }
  },
  computed: {
    ...mapGetters([GET_TAG_ID_BY_NAME]),
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
    closeModal() {
      this.dialog = false;
    },
    openModal() {
      this.dialog = true;
    },
    clearInputTagForm() {
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
        this.bookmarkId = await this[CREATE_BOOKMARK]({ url: this.url });
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmarkId });
        this[SHOW_SNACKBAR](MESSAGES.BOOKMARK.ADD.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.BOOKMARK.ADD.FAIL);
      }
    },
    async onAddTagBookmark(targetTagName) {
      if (this.tags.length === 0) {
        return;
      }
      if (!this.$refs.tagForm.validate()) {
        this.tags.splice(this.tags.indexOf(targetTagName), 1);
        this.tags = [...this.tags];
        return;
      }
      try {
        const targetTagId = await this[CREATE_TAG]({ name: targetTagName });
        await this[CREATE_TAG_BOOKMARK]({ tagId: targetTagId, bookmarkId: this.bookmarkId });
        await this[FETCH_CATEGORIES]();
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmarkId });
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.ADD.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.ADD.FAIL);
      }
    },
    async onDeleteTagBookmark(targetTagName) {
      if (this.tags.length === 0) {
        return;
      }
      const targetTagId = this[GET_TAG_ID_BY_NAME](targetTagName);
      try {
        await this[DELETE_TAG_BOOKMARK]({ tagId: targetTagId, bookmarkId: this.bookmarkId });
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmarkId });
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.SUCCESS);
        if (this.tags.indexOf(targetTagName) !== -1) {
          this.tags.splice(this.tags.indexOf(targetTagName), 1);
          this.tags = [...this.tags];
        }
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.FAIL);
      }
    },
  },
};
</script>
