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
import { BOOKMARK_WITH_TAGS, GET_TAG_ID_BY_NAME } from '@/store/share/getterTypes.js';
import {
  FETCH_BOOKMARK_DETAIL,
  CREATE_TAG_BOOKMARK,
  DELETE_TAG_BOOKMARK,
  FETCH_CATEGORIES,
  CREATE_TAG,
} from '@/store/share/actionTypes.js';
import { MESSAGES } from '@/utils/constants.js';

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
    ...mapGetters([BOOKMARK_WITH_TAGS, GET_TAG_ID_BY_NAME]),
  },
  data() {
    return {
      dialog: false,
      tag: '',
      tags: [],
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
    async onAddTagBookmark(data) {
      const targetTagName = data.tag.text;
      try {
        const targetTagId = await this[CREATE_TAG]({ name: targetTagName });
        await this[CREATE_TAG_BOOKMARK]({ tagId: targetTagId, bookmarkId: this.bookmark.id });
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmark.id });
        await this[FETCH_CATEGORIES]();
        data.addTag();
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.ADD.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.ADD.FAIL);
      }
    },
    async onDeleteTagBookmark(data) {
      const targetTagName = data.tag.text;
      const targetTagId = this[GET_TAG_ID_BY_NAME](targetTagName);
      try {
        await this[DELETE_TAG_BOOKMARK]({ tagId: targetTagId, bookmarkId: this.bookmark.id });
        await this[FETCH_BOOKMARK_DETAIL]({ bookmarkId: this.bookmark.id });
        data.deleteTag();
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG_WITH_BOOKMARKS.DELETE.FAIL);
      }
    },
    initTags() {
      this.tags = this[BOOKMARK_WITH_TAGS].map((tag) => this.mapTag(tag));
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
