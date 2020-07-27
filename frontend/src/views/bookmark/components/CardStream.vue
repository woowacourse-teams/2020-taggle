<template>
  <v-layout row wrap>
    <v-flex v-for="bookmark in bookmarks" :key="bookmark.id" xs12>
      <v-card>
        <div class="d-flex flex-no-wrap">
          <v-avatar class="ma-3" size="125" tile>
            <v-img :src="bookmark.img"></v-img>
          </v-avatar>
          <div>
            <v-card-title class="headline">{{ bookmark.title }}</v-card-title>
            <v-card-subtitle>
              <v-chip v-for="tag in bookmark.tags" :key="tag.id">{{ tag.name }}</v-chip>
            </v-card-subtitle>
          </div>
        </div>
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
