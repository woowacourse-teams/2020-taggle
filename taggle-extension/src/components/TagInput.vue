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
  CREATE_BOOKMARK,
} from '../store/share/actionsType.js';
import { mapActions, mapGetters } from 'vuex';

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
    ...mapGetters(['bookmarkId', 'tagBookmarks']),
  },
  async created() {
    await this.createBookmark(this.bookmarkCreateRequest);
  },
  methods: {
    ...mapActions([CREATE_BOOKMARK, ADD_TAG_BOOKMARK, CREATE_TAG, DELETE_TAG_BOOKMARK]),
    async onAddTagBookmark(data) {
      this.tagCreateRequest.name = data.tag.text;
      const tagId = await this.createTag(this.tagCreateRequest);
      await this.addTagBookmark({
        bookmarkId: this.bookmarkId,
        tagId,
      });
      data.addTag();
    },
    async onRemoveTagBookmark(data) {
      const deleteName = data.tag.text;
      const tagId = this.$store.getters.getTagIdByName(deleteName);
      console.log(this.bookmarkId, tagId, deleteName);
      await this.removeTagBookmark({
        bookmarkId: this.bookmarkId,
        tagId,
      });
      data.deleteTag();
    },
  },
};
</script>
