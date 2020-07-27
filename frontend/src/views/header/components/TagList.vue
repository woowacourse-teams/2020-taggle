<template>
  <v-card width="250" tile id="container">
    <v-list class="pa-0" v-for="{ id, title, tags } in tagList" :key="id">
      <v-list-group value="true" class="text-center">
        <template v-slot:activator>
          <v-list-item-title>{{ title }}</v-list-item-title>
        </template>

        <v-list-item v-for="{ id, name } in tags" :key="id">
          <v-list-item-icon>
            <v-chip class="ma-auto" color="pink" label text-color="white">
              <v-icon left>mdi-label</v-icon>
              {{ name }}
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
