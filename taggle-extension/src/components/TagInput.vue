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
import VueTagsInput from '@johmun/vue-tags-input';

export default {
  components: {
    VueTagsInput,
  },
  data() {
    return {
      tag: '',
      tags: [],
    };
  },
  methods: {
    addTagToBookmark(data) {
      TagService.create({ name: data.tag.text });
      data.addTag();
    },
  },
};
</script>
