<template>
  <div class="mt-1">
    <vue-tags-input v-model="tag" :tags="tags" @tags-changed="newTags => (tags = newTags)" placeholder="태그 추가" />
  </div>
</template>

<script>
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
};
</script>
