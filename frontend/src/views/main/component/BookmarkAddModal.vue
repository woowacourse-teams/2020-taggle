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
              :rules="rules"
              :disabled="isBookmarkAdded"
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
import VueTagsInput from '@johmun/vue-tags-input';

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
        this.bookmarkId = await this[CREATE_BOOKMARK]({ url: this.url });
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmarkId });
        this[SHOW_SNACKBAR](MESSAGES.BOOKMARK.ADD.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.BOOKMARK.ADD.FAIL);
      }
    },
    async onAddTagBookmark(data) {
      const targetTagName = data.tag.text;
      try {
        const targetTagId = await this[CREATE_TAG]({ name: targetTagName });
        await this[CREATE_TAG_BOOKMARK]({ tagId: targetTagId, bookmarkId: this.bookmarkId });
        await this[FETCH_CATEGORIES]();
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmarkId });
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
        await this[DELETE_TAG_BOOKMARK]({ tagId: targetTagId, bookmarkId: this.bookmarkId });
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmarkId });
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.SUCCESS);
        data.deleteTag();
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.FAIL);
      }
    },
  },
};
</script>
