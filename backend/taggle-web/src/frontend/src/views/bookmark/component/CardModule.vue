<template>
  <v-row>
    <v-col cols="12">
      <v-row justify="start">
        <CardModuleItem v-for="bookmark in bookmarks" :key="bookmark.id" :bookmark="bookmark" />
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
import CardModuleItem from '@/views/bookmark/component/CardModuleItem.vue';

export default {
  name: 'CardModule',
  components: {
    CardModuleItem,
  },
  props: {
    bookmarks: Array,
  },
};
</script>

<style>
.title-wrap {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.content-wrap {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
</style>
