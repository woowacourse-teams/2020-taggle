<template>
  <v-row>
    <v-col cols="12">
      <v-row justify="center">
        <v-card
          :href="bookmark.link"
          :key="bookmark.id"
          class="ma-5"
          link
          outlined
          target="_blank"
          v-for="bookmark in bookmarks"
          width="300"
        >
          <v-responsive aspect-ratio="0.8">
            <v-img :src="bookmark.img" aspect-ratio="1.6"></v-img>
            <div class="test-size">
              <v-card-title primary-title>
                <span class="title-wrap">
                  {{ bookmark.title }}
                </span>
              </v-card-title>
              <v-card-text>helloworld</v-card-text>
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
</style>
