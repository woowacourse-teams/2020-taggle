<template>
  <v-row>
    <CardStreamItem v-for="bookmark in bookmarks" :key="bookmark.id" :bookmark="bookmark" />
  </v-row>
</template>

<script>
import CardStreamItem from '@/views/bookmark/components/CardStreamItem.vue';

export default {
  name: 'CardModule',
  components: {
    CardStreamItem,
  },
  props: {
    bookmarks: Array,
  },
};
</script>
