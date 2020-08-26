<template>
  <v-card-actions>
    <v-spacer></v-spacer>
    <TagEditModal :bookmark="bookmark" />
  </v-card-actions>
</template>

<script>
import TagEditModal from '@/views/bookmark/components/TagEditModal.vue';

export default {
  name: 'CardIcons',
  components: {
    TagEditModal,
  },
  props: {
    bookmark: {
      type: Object,
      required: true,
    },
  },
};
</script>
