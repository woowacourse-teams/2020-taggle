<template>
  <v-row>
    <v-col cols="12">
      <v-row justify="flex-start">
        <v-card
          v-for="bookmark in bookmarks"
          :key="bookmark.id"
          :href="bookmark.link"
          link
          class="ma-5"
          outlined
          target="_blank"
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
              <v-card-text>
                <span class="content-wrap">
                  Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium,
                  totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta
                  sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia
                  consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui
                  dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora
                  incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum
                  exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis
                  autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel
                  illum qui dolorem eum fugiat quo voluptas nulla pariatur?
                </span>
              </v-card-text>
            </div>
          </v-responsive>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon>
              <v-icon>mdi-heart</v-icon>
            </v-btn>
            <v-btn icon>
              <v-icon>mdi-share-variant</v-icon>
            </v-btn>
          </v-card-actions>
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

.content-wrap {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
}
</style>
