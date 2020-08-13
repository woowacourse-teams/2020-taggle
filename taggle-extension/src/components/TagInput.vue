<template>
  <div class="mt-1">
    <vue-tags-input
      v-model="tag"
      :tags="tags"
      @before-deleting-tag="onRemoveTagBookmark"
      @tags-changed="(newTags) => (tags = newTags)"
      @before-adding-tag="onAddTagBookmark"
      placeholder="태그 추가"
    />
  </div>
</template>

<script>
import VueTagsInput from '@johmun/vue-tags-input';
import { mapMutations, mapActions, mapGetters } from 'vuex';
import {
  ADD_TAG_BOOKMARK,
  CREATE_TAG,
  DELETE_TAG_BOOKMARK,
  CREATE_BOOKMARK,
  FETCH_TAG_BOOKMARK,
} from '../store/share/actionsType.js';
import { BOOKMARK_ID, TAG_ID_BY_NAME } from '../store/share/gettersType.js';
import { SHOW_SNACKBAR } from '../store/share/mutationsType.js';
import { SNACKBAR_MESSAGES } from '../utils/constants.js';

export default {
  name: 'TagInput',
  components: {
    VueTagsInput,
  },
  props: {
    bookmarkUrl: {
      type: String,
      require: true,
    },
  },
  data() {
    return {
      tag: '',
      tags: [],
      tagCreateRequest: {
        name: '',
      },
      bookmarkCreateRequest: {
        url: this.bookmarkUrl,
      },
    };
  },
  computed: {
    ...mapGetters([BOOKMARK_ID, TAG_ID_BY_NAME]),
  },
  async created() {
    try {
      const bookmarkId = await this[CREATE_BOOKMARK](this.bookmarkCreateRequest);
      await this[FETCH_TAG_BOOKMARK](bookmarkId);
    } catch (e) {
      this[SHOW_SNACKBAR](SNACKBAR_MESSAGES.BOOKMARK.ADD.FAIL);
    }
  },
  methods: {
    ...mapMutations([SHOW_SNACKBAR]),
    ...mapActions([CREATE_BOOKMARK, ADD_TAG_BOOKMARK, CREATE_TAG, DELETE_TAG_BOOKMARK, FETCH_TAG_BOOKMARK]),
    async onAddTagBookmark(data) {
      this.tagCreateRequest.name = data.tag.text;
      try {
        const tagId = await this[CREATE_TAG](this.tagCreateRequest);
        await this[ADD_TAG_BOOKMARK]({
          bookmarkId: this[BOOKMARK_ID],
          tagId,
        });
        await this[FETCH_TAG_BOOKMARK](this[BOOKMARK_ID]);
        data.addTag();
      } catch (e) {
        this[SHOW_SNACKBAR](SNACKBAR_MESSAGES.TAG_BOOKMARK.ADD.FAIL);
      }
    },
    async onRemoveTagBookmark(data) {
      const deleteName = data.tag.text;
      const tagId = this[TAG_ID_BY_NAME](deleteName);
      try {
        await this[DELETE_TAG_BOOKMARK]({
          bookmarkId: this[BOOKMARK_ID],
          tagId,
        });
        await this[FETCH_TAG_BOOKMARK](this[BOOKMARK_ID]);
        data.deleteTag();
      } catch (e) {
        this[SHOW_SNACKBAR](SNACKBAR_MESSAGES.TAG_BOOKMARK.DELETE.FAIL);
      }
    },
  },
};
</script>
