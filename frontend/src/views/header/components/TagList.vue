<template>
  <v-card width="250" tile id="container">
    <v-list fill-height class="pa-0" v-for="(tagValue, i) in tagList" :key="i">
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
export default {
  name: 'TagList',
  data() {
    return {
      tagList: [
        {
          title: '백엔드',
          tags: ['#자바', '#노드', '#스프링']
        },
        {
          title: '프론트엔드',
          tags: ['#자바스크립트', '#Jun', '#...']
        },
        {
          title: 'UI/UX',
          tags: ['#디자인', '#개떡같다', '#디자인에 문제있어?']
        }
      ]
    }
  }
}
</script>

<style scoped>
#container {
  position: fixed;
  left: 56px;
  overflow: auto;
  height: 100%;
}
</style>
