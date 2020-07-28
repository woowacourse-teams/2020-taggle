<template>
  <v-layout row wrap>
    <v-flex v-for="bookmark in bookmarks" :key="bookmark.id" xs12 md4>
      <v-card outlined>
        <v-responsive aspect-ratio="0.66">
          <v-img :src="bookmark.img" aspect-ratio="1.33"></v-img>
          <div class="test-size">
            <v-card-title primary-title>
              {{ bookmark.title }}
            </v-card-title>
            <v-chip v-for="tag in bookmark.tags" :key="tag.id">{{ tag.name }}</v-chip>
          </div>
        </v-responsive>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  name: 'CardModule',
  props: {
    bookmarks: Array,
  },
  data() {
    return {};
  },
};
</script>
