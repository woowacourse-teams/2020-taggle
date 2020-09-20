<template>
  <v-btn icon @click.prevent="openModal">
    <v-icon>mdi-pencil</v-icon>
    <v-dialog v-model="dialog" max-width="500">
      <v-card>
        <v-app-bar dense flat>
          <v-toolbar-title>태그 편집</v-toolbar-title>
          <v-spacer />
          <v-btn icon @click="closeModal">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-app-bar>
        <v-card-text>
          <v-form ref="tagForm">
            <v-combobox
              v-model="tags"
              label="해당 북마크에 태그를 추가하거나 삭제할 수 있습니다."
              :rules="rules"
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
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { BOOKMARK_WITH_TAGS, GET_TAG_ID_BY_NAME, IS_TAGS_EMPTY } from '@/store/share/getterTypes.js';
import {
  FETCH_BOOKMARK_DETAIL,
  CREATE_TAG_BOOKMARK,
  DELETE_TAG_BOOKMARK,
  FETCH_CATEGORIES,
  CREATE_TAG,
} from '@/store/share/actionTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import validator from '@/utils/validator.js';

export default {
  name: 'TagEditModal',
  props: {
    bookmark: {
      type: Object,
      required: true,
    },
  },
  computed: {
    ...mapGetters([BOOKMARK_WITH_TAGS, GET_TAG_ID_BY_NAME, IS_TAGS_EMPTY]),
  },
  data() {
    return {
      dialog: false,
      tags: [],
      isTagsLoaded: false,
      rules: validator.tag.name,
    };
  },
  watch: {
    async dialog() {
      if (this.dialog) {
        try {
          await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmark.id });
          this.initTags();
        } catch (e) {
          this[SHOW_SNACKBAR](MESSAGES.BOOKMARK_WITH_TAGS.FETCH.FAIL);
        }
      }
    },
    tags(newTags, oldTags) {
      if (this.dialog && !this.isTagsLoaded) {
        this.isTagsLoaded = true;
        return;
      }
      if (newTags.length > oldTags.length) {
        this.onAddTagBookmark(newTags[newTags.length - 1]);
      }
      if (newTags.length < oldTags.length) {
        this.onDeleteTagBookmark(oldTags[oldTags.length - 1]);
      }
    }
  },
  methods: {
    ...mapActions([FETCH_BOOKMARK_DETAIL, FETCH_CATEGORIES, CREATE_TAG_BOOKMARK, CREATE_TAG, DELETE_TAG_BOOKMARK]),
    ...mapMutations([SHOW_SNACKBAR]),
    closeModal() {
      this.dialog = false;
    },
    openModal() {
      this.dialog = true;
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
        await this[CREATE_TAG_BOOKMARK]({ tagId: targetTagId, bookmarkId: this.bookmark.id });
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmark.id });
        await this[FETCH_CATEGORIES]();
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
        await this[DELETE_TAG_BOOKMARK]({ tagId: targetTagId, bookmarkId: this.bookmark.id });
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmark.id });
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.SUCCESS);
        if (this.tags.indexOf(targetTagName) !== -1) {
          this.tags.splice(this.tags.indexOf(targetTagName), 1);
          this.tags = [...this.tags];
        }
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.FAIL);
      }
    },
    initTags() {
      if (this[IS_TAGS_EMPTY]) {
        return;
      }
      this.tags = this[BOOKMARK_WITH_TAGS].map((tag) => tag.name);
    },
  },
};
</script>
