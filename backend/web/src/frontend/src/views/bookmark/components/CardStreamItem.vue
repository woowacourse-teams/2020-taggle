<template>
  <v-col cols="12">
    <v-card :href="bookmark.url" class="d-flex" link outlined target="_blank">
      <v-col align-self="center" class="flex-grow-0">
        <v-avatar class="m-2" size="170" tile>
          <v-img :src="bookmark.image"></v-img>
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
            {{ bookmark.description }}
          </span>
        </v-card-text>
        <CardIcons :bookmark="bookmark" />
      </v-list-item-content>
    </v-card>
  </v-col>
</template>

<script>
import CardIcons from '@/views/bookmark/components/CardIcons.vue';

export default {
  name: 'CardStreamItem',
  components: {
    CardIcons,
  },
  props: {
    bookmark: {
      type: Object,
      required: true,
    },
  },
};
</script>
