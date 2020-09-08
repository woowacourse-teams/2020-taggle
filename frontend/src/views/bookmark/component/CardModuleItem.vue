<template>
  <v-card :href="bookmark.url" link class="ma-5" outlined target="_blank" width="300">
    <v-responsive aspect-ratio="0.75">
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
    <CardIcons :bookmark="bookmark" />
  </v-card>
</template>

<script>
import CardIcons from '@/views/bookmark/component/CardIcons.vue';

export default {
  name: 'CardStreamItem',
  components: { CardIcons },
  props: {
    bookmark: {
      type: Object,
      required: true,
    },
  },
};
</script>
