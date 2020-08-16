<template>
  <v-card :href="bookmark.url" link class="ma-5" outlined target="_blank" width="300">
    <v-responsive aspect-ratio="0.8">
      <v-img :src="bookmark.image" aspect-ratio="1.6"></v-img>
      <div class="test-size">
        <v-card-title primary-title>
          <span class="title-wrap">
            {{ bookmark.title }}
          </span>
        </v-card-title>
        <v-card-text>
          <span class="content-wrap">
            {{ bookmark.description }}
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
      <TagEditModal :bookmark="bookmark" />
    </v-card-actions>
  </v-card>
</template>

<script>
import TagEditModal from '@/views/bookmark/components/TagEditModal.vue';

export default {
  name: 'CardStreamItem',
  components: { TagEditModal },
  props: {
    bookmark: {
      type: Object,
      required: true,
    },
  },
};
</script>
