<template>
  <v-card-actions>
    <v-spacer></v-spacer>
    <v-btn icon>
      <v-icon>mdi-heart</v-icon>
    </v-btn>
    <v-btn icon>
      <v-icon>mdi-share-variant</v-icon>
    </v-btn>
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
