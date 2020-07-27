<template>
  <v-layout row wrap>
    <v-flex v-for="bookmark in bookmarks" :key="bookmark.id" xs4>
      <v-card>
        <v-img :src="bookmark.img" aspect-ratio="2.75"></v-img>
        <v-card-title primary-title>
          <div>
            <h3 class="headline mb-0">{{ bookmark.title }}</h3>
          </div>
        </v-card-title>
        <v-chip v-for="tag in bookmark.tags" :key="tag.id">{{ tag.name }}</v-chip>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  name: 'CardModule',
  props: {
    bookmarks: Array
  },
  data() {
    return {};
  }
};
</script>
