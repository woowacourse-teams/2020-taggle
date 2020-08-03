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
import {
  ADD_TAG_BOOKMARK,
  CREATE_TAG,
  DELETE_TAG_BOOKMARK,
  FETCH_OR_CREATE_BOOKMARK,
} from '../store/share/actionsType.js';
import { mapGetters } from 'vuex';

export default {
  name: 'TagInput',
  components: {
    VueTagsInput,
  },
  created() {
    this.fetchBookmark();
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
    ...mapGetters(['bookmarkId', 'tagBookmarks']),
  },
  methods: {
    async fetchBookmark() {
      await this.$store.dispatch(FETCH_OR_CREATE_BOOKMARK, this.bookmarkCreateRequest);
    },
    async onAddTagBookmark(data) {
      this.tagCreateRequest.name = data.tag.text;
      const tagId = await this.$store.dispatch(CREATE_TAG, this.tagCreateRequest);
      await this.$store.dispatch(ADD_TAG_BOOKMARK, {
        bookmarkId: this.bookmarkId,
        tagId,
      });
      data.addTag();
    },
    async onRemoveTagBookmark(data) {
      const deleteName = data.tag.text;
      const tagId = this.$store.getters.getTagIdByName(deleteName);
      await this.$store.dispatch(DELETE_TAG_BOOKMARK, {
        bookmarkId: this.bookmarkId,
        tagId,
      });
      data.deleteTag();
    },
  },
};
</script>
