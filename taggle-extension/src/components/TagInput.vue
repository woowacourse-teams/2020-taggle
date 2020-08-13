<template>
  <div class="mt-1">
    <vue-tags-input
      :tags="tags"
      @before-adding-tag="addTagToBookmark"
      @tags-changed="(newTags) => (tags = newTags)"
      placeholder="태그 추가"
      v-model="tag"
    />
  </div>
</template>

<script>
import VueTagsInput from '@johmun/vue-tags-input';
import TagService from '../api/module/tag.js';
import TagBookmarkService from '../api/module/tagBookmark.js';

export default {
  name: 'TagInput',
  components: {
    VueTagsInput,
  },
  props: {
    bookmarkId: {
      type: String,
    },
  },
  data() {
    return {
      tag: '',
      tags: [],
    };
  },
  methods: {
    async addTagToBookmark(data) {
      const tagId = await TagService.create({ name: data.tag.text });
      TagBookmarkService.create({ tagId, bookmarkId: this.bookmarkId });
      data.addTag();
    },
  },
};
</script>
