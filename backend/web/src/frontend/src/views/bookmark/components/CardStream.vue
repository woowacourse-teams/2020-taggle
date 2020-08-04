<template>
  <v-row>
    <v-col :key="bookmark.id" cols="12" v-for="bookmark in bookmarks">
      <v-card :href="bookmark.link" link outlined target="_blank">
        <div class="d-flex flex-no-wrap">
          <v-avatar class="ma-3" size="125" tile>
            <v-img :src="bookmark.img"></v-img>
          </v-avatar>
          <div>
            <!--:href="bookmarks.link" link="true" target="_blank"-->
            <v-card-title primary-title>
              <span class="title-wrap">
                {{ bookmark.title }}
              </span>
            </v-card-title>
            <v-card-text>helloworld</v-card-text>
          </div>
        </div>
      </v-card>
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
