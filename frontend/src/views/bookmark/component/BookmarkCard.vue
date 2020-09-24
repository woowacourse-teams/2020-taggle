<template>
  <v-row>
    <v-col>
      <CardStream :bookmarks="bookmarks" v-show="cardDisplayMode === 'stream'" />
      <CardModule :bookmarks="bookmarks" v-show="cardDisplayMode === 'module'" />
    </v-col>
    <v-col cols="1">
      <div class="card-change-button-container">
        <v-icon @click="changeCardDisplayMode('stream')" x-large>view_stream</v-icon>
        <v-icon @click="changeCardDisplayMode('module')" x-large>view_module</v-icon>
      </div>
    </v-col>
  </v-row>
</template>

<script>
import CardStream from '@/views/bookmark/component/CardStream.vue';
import CardModule from '@/views/bookmark/component/CardModule.vue';

export default {
  name: 'BookmarkCard',
  components: {
    CardStream,
    CardModule,
  },
  props: {
    bookmarks: Array,
  },
  data() {
    return {
      cardDisplayMode: 'stream',
    };
  },
  methods: {
    changeCardDisplayMode(cardType) {
      this.cardDisplayMode = cardType;
    },
  },
};
</script>

<style scoped>
.card-change-button-container {
  position: fixed;
}
</style>
