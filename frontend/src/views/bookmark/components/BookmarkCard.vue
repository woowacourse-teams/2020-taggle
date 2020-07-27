<template>
  <v-layout row wrap class="mt-10 layout-container" style="position:absolute">
    <v-flex xs3></v-flex>
    <v-flex xs7>
      <v-container grid-list-lg text-xs-center>
        <CardStream :bookmarks="bookmarks" v-if="flexOption" />
        <CardModule :bookmarks="bookmarks" v-else />
      </v-container>
    </v-flex>
    <v-flex xs2>
      <v-icon @click="isOption('stream')" x-large>view_stream</v-icon>
      <v-icon @click="isOption('module')" x-large>view_module</v-icon>
    </v-flex>
  </v-layout>
</template>

<script>
import CardStream from './CardStream.vue';
import CardModule from './CardModule.vue';

export default {
  name: 'BookmarkCard',
  components: {
    CardStream,
    CardModule
  },
  data() {
    return {
      flexOption: true,
      bookmarks: []
    };
  },
  created() {
    this.setBookmarkData();
  },
  methods: {
    setBookmarkData: function() {
      for (let i = 0; i < 10; i++) {
        this.bookmarks.push({
          img: 'https://t1.daumcdn.net/cfile/tistory/212EFE4D58D72EB70D',
          title: 'Test Case' + i,
          tags: [{ name: 'Java' }, { name: 'Spring' }, { name: '방구' }]
        });
      }
    },
    isOption: function(type) {
      if (type == 'stream') {
        this.flexOption = true;
      } else {
        this.flexOption = false;
      }
    }
  }
};
</script>

<style scoped>
.layout-container {
  width: 100%;
}
</style>
