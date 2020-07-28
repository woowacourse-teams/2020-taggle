<template>
  <v-card width="250" tile id="container">
    <v-list class="pa-0" v-for="{ id, title, tags } in tagList" :key="id">
      <v-list-group value="true" class="text-left mt-2">
        <template v-slot:activator>
          <v-list-item-title class="font-weight-black text-h6">{{ title }}</v-list-item-title>
        </template>
        <v-list-item v-for="{ id, name } in tags" :key="id">
          <v-list-item-icon class="mt-0 mb-0 text-button">
            {{ name }}
          </v-list-item-icon>
        </v-list-item>
      </v-list-group>
    </v-list>
  </v-card>
</template>

<script>
import { tagList } from '@/utils/mockTags';

export default {
  name: 'TagList',
  data() {
    return {
      tagList: tagList,
    };
  },
};
</script>

<style>
#container {
  position: fixed;
  left: 56px;
  height: 100%;
  z-index: 1;
}

.v-list-item {
  min-height: 20px !important;
}
</style>
