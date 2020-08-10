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
import { mapActions, mapGetters } from 'vuex';
import {
  ADD_TAG_BOOKMARK,
  CREATE_TAG,
  DELETE_TAG_BOOKMARK,
  CREATE_BOOKMARK,
  FETCH_TAG_BOOKMARK,
} from '../store/share/actionsType.js';
import { BOOKMARK_ID, TAG_ID_BY_NAME } from '../store/share/gettersType.js';

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
    const bookmarkId = await this[CREATE_BOOKMARK](this.bookmarkCreateRequest);
    await this[FETCH_TAG_BOOKMARK](bookmarkId);
  },
  methods: {
    ...mapActions([CREATE_BOOKMARK, ADD_TAG_BOOKMARK, CREATE_TAG, DELETE_TAG_BOOKMARK, FETCH_TAG_BOOKMARK]),
    async onAddTagBookmark(data) {
      this.tagCreateRequest.name = data.tag.text;
      const tagId = await this[CREATE_TAG](this.tagCreateRequest);
      await this[ADD_TAG_BOOKMARK]({
        bookmarkId: this[BOOKMARK_ID],
        tagId,
      });
      await this[FETCH_TAG_BOOKMARK](this[BOOKMARK_ID]);
      data.addTag();
    },
    async onRemoveTagBookmark(data) {
      const deleteName = data.tag.text;
      const tagId = this[TAG_ID_BY_NAME](deleteName);
      await this[DELETE_TAG_BOOKMARK]({
        bookmarkId: this[BOOKMARK_ID],
        tagId,
      });
      await this[FETCH_TAG_BOOKMARK](this[BOOKMARK_ID]);
      data.deleteTag();
    },
  },
};
</script>
