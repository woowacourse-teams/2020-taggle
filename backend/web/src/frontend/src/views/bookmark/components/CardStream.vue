<template>
  <v-row>
    <CardStreamItem v-for="bookmark in bookmarks" :key="bookmark.id" :bookmark="bookmark" />
  </v-row>
</template>

<script>
import CardStreamItem from './CardStreamItem.vue';

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
