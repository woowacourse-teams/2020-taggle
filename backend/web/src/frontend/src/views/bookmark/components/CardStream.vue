<template>
  <v-row>
    <v-col v-for="bookmark in bookmarks" :key="bookmark.id" cols="12">
      <v-card :href="bookmark.link" class="d-flex" link outlined target="_blank">
        <v-col align-self="center" class="flex-grow-0">
          <v-avatar class="m-2" size="170" tile>
            <v-img :src="bookmark.img"></v-img>
          </v-avatar>
        </v-col>
        <v-list-item-content class="flex-grow-1">
          <v-card-title primary-title>
            <span class="title-wrap">
              {{ bookmark.title }}
            </span>
          </v-card-title>
          <v-card-text>
            <span class="content-wrap">
              Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam
              rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt
              explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia
              consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui
              dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora
              incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum
              exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem
              vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui
              dolorem eum fugiat quo voluptas nulla pariatur?
            </span>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon>
              <v-icon>mdi-heart</v-icon>
            </v-btn>
            <v-btn icon>
              <v-icon>mdi-share-variant</v-icon>
            </v-btn>
          </v-card-actions>
        </v-list-item-content>
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
