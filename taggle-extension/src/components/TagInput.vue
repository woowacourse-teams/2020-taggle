<template>
  <div class="mt-1">
    <vue-tags-input
      v-model="tag"
      :tags="tags"
      @tags-changed="(newTags) => (tags = newTags)"
      @before-adding-tag="addTagToBookmark"
      placeholder="태그 추가"
    />
  </div>
</template>

<script>
import TagService from '../api/module/tag.js';
import TagBookmarkService from '../api/module/tagBookmark.js';
import VueTagsInput from '@johmun/vue-tags-input';

export default {
  name: "TagInput",
  components: {
    VueTagsInput,
  },
  props: {
    bookmarkId: {
      type: String
    }
  },
  data() {
    return {
      tag: '',
      tags: [],
    };
  },
  methods: {
    addTagToBookmark: async function(data) {
      const tagId = await TagService.create({ name: data.tag.text });
      TagBookmarkService.create(this.bookmarkId, tagId);
      data.addTag();
    },
  },
};
</script>
