<template>
  <div class="mt-1">
    <v-form ref="tagForm">
      <v-combobox
        v-model="tags"
        label="태그 추가"
        :rules="rules"
        class="mt-2"
        small-chips
        multiple
        outlined
        hide-details
        dense
      >
        <template v-slot:selection="{ attrs, item, select, selected }">
          <v-chip
            class="mt-1"
            v-bind="attrs"
            color="light-blue lighten-1"
            text-color="white"
            :input-value="selected"
            @click="select"
            close
            x-small
            @click:close="onDeleteTagBookmark(item)"
          >
            <strong>{{ item }}</strong>
          </v-chip>
        </template>
      </v-combobox>
    </v-form>
  </div>
</template>

<script>
import { mapMutations, mapActions, mapGetters } from 'vuex';
import {
  CREATE_TAG_BOOKMARK,
  CREATE_TAG,
  DELETE_TAG_BOOKMARK,
  FETCH_BOOKMARK_DETAIL,
} from '@/store/share/actionTypes.js';
import { BOOKMARK_ID, BOOKMARK_WITH_TAGS, GET_TAG_ID_BY_NAME } from '@/store/share/getterTypes.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { SNACKBAR_MESSAGES } from '@/utils/constants.js';
import validator from '@/utils/validator.js';

export default {
  name: 'TagInput',
  data() {
    return {
      tags: [],
      isTagsLoaded: false,
      rules: validator.tag.name,
      tagCreateRequest: {
        name: '',
      },
    };
  },
  computed: {
    ...mapGetters([BOOKMARK_ID, GET_TAG_ID_BY_NAME, BOOKMARK_WITH_TAGS]),
  },
  watch: {
    async [BOOKMARK_ID]() {
      if (this[BOOKMARK_ID]) {
        try {
          await this[FETCH_BOOKMARK_DETAIL](this[BOOKMARK_ID]);
          await this.initTags();
        } catch (e) {
          this[SHOW_SNACKBAR](SNACKBAR_MESSAGES.TAG_BOOKMARK.FETCH.FAIL);
        }
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
  methods: {
    ...mapMutations([SHOW_SNACKBAR]),
    ...mapActions([CREATE_TAG_BOOKMARK, CREATE_TAG, DELETE_TAG_BOOKMARK, FETCH_BOOKMARK_DETAIL]),
    async onAddTagBookmark(tagName) {
      if (this.tags.length > 10 || !this.$refs.tagForm.validate()) {
        this.tags.splice(this.tags.indexOf(tagName), 1);
        return;
      }
      try {
        const targetTagId = await this[CREATE_TAG]({ name: tagName });
        await this[CREATE_TAG_BOOKMARK]({ tagId: targetTagId, bookmarkId: this[BOOKMARK_ID] });
        await this[FETCH_BOOKMARK_DETAIL](this[BOOKMARK_ID]);
        this[SHOW_SNACKBAR](SNACKBAR_MESSAGES.TAG_BOOKMARK.ADD.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](SNACKBAR_MESSAGES.TAG_BOOKMARK.ADD.FAIL);
      }
    },
    async onDeleteTagBookmark(tagName) {
      try {
        const targetTagId = this[GET_TAG_ID_BY_NAME](tagName);
        await this[DELETE_TAG_BOOKMARK]({ bookmarkId: this[BOOKMARK_ID], tagId: targetTagId });
        await this[FETCH_BOOKMARK_DETAIL](this[BOOKMARK_ID]);
        this[SHOW_SNACKBAR](SNACKBAR_MESSAGES.TAG_BOOKMARK.DELETE.SUCCESS);
        if (this.tags.indexOf(tagName) !== -1) {
          this.tags.splice(this.tags.indexOf(tagName), 1);
        }
      } catch (e) {
        this[SHOW_SNACKBAR](SNACKBAR_MESSAGES.TAG_BOOKMARK.DELETE.FAIL);
      }
    },
    async initTags() {
      try {
        this.isTagsLoaded = false;
        await this[FETCH_BOOKMARK_DETAIL](this[BOOKMARK_ID]);
        this.tags = this[BOOKMARK_WITH_TAGS].map((tag) => tag.name);
      } catch (e) {
        this[SHOW_SNACKBAR](SNACKBAR_MESSAGES.BOOKMARK_WITH_TAGS.FETCH.FAIL);
      }
    },
  },
};
</script>
