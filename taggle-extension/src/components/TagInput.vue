<template>
  <vue-tags-input v-model="tag" :tags="tags" @tags-changed="newTags => (tags = newTags)" placeholder="태그 추가" />
</template>

<script>
import VueTagsInput from '@johmun/vue-tags-input';

export default {
  components: {
    VueTagsInput,
  },
  date() {
    return {
      tag: '',
      tags: [],
    };
  },
};
</script>
