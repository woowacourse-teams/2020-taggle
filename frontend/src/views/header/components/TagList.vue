<template>
  <v-card width="250" tile id="container">
    <v-list class="pa-0" v-for="(tagValue, i) in tagList" :key="i">
      <v-list-group value="true" class="text-center">
        <template v-slot:activator>
          <v-list-item-title>{{ tagValue.title }}</v-list-item-title>
        </template>

        <v-list-item v-for="(tag, i) in tagValue.tags" :key="i">
          <v-list-item-icon>
            <v-chip class="ma-auto" color="pink" label text-color="white">
              <v-icon left>mdi-label</v-icon>
              {{ tag }}
            </v-chip>
          </v-list-item-icon>
        </v-list-item>
      </v-list-group>
    </v-list>
  </v-card>
</template>

<script>
import { tagList } from '@/utils/mockTags.js'

export default {
  name: 'TagList',
  data() {
    return {
      tagList: tagList
    }
  }
}
</script>

<style scoped>
#container {
  position: fixed;
  left: 56px;
  height: 100%;
  z-index: 1;
}
</style>
