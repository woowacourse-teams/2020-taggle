<template>
  <v-row>
    <v-col cols="12">
      <v-row justify="center">
        <v-card v-for="bookmark in bookmarks" :key="bookmark.id" class="ma-5" outlined width="300">
          <v-responsive aspect-ratio="0.8">
            <v-img :src="bookmark.img" aspect-ratio="1.6"></v-img>
            <div class="test-size">
              <v-card-title primary-title>
                {{ bookmark.title }}
              </v-card-title>
              <v-chip v-for="tag in bookmark.tags" :key="tag.id">{{ tag.name }}</v-chip>
            </div>
          </v-responsive>
        </v-card>
      </v-row>
    </v-col>
  </v-row>
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
