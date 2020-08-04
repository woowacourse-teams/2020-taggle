<template>
  <v-card id="container" tile width="250">
    <v-list :key="id" class="pa-0" v-for="{ id, title, tags } in categories">
      <v-list-group class="text-left mt-2" value="true">
        <template v-slot:activator>
          <v-list-item-title class="font-weight-black text-h7">{{ title }}</v-list-item-title>
        </template>
        <v-list-item :key="id" @click.prevent="fetchBookmarks" v-for="{ id, name } in tags">
          <v-list-item-content>
            {{ name }}
          </v-list-item-content>
        </v-list-item>
      </v-list-group>
    </v-list>
  </v-card>
</template>

<script>
import { categories } from '@/utils/mockTags.js';

export default {
  name: 'TagList',
  data() {
    return {
      categories,
    };
  },
  methods: {
    fetchBookmarks() {
      if (this.$router.currentRoute.path !== '/bookmark') {
        this.$router.replace('/bookmark');
      }
      // this.$router.push(`tags/${tagId}/bookmarks`);
    },
  },
};
</script>

<style>
#container {
  position: fixed;
  left: 56px;
  height: 100%;
}
</style>
