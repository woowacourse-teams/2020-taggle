<template>
  <v-card width="250" tile id="container">
    <v-list>
      <v-list-group value="true" v-for="(tagValue, i) in tagList" :key="i" class="text-center">
        <template v-slot:activator>
          <v-list-item-title>{{ tagValue.title }}</v-list-item-title>
        </template>

        <v-list-item v-for="(tag, i) in tagValue.tags" :key="i" link>
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
        }
      ]
    }
  }
}
</script>

<style scoped>
#container {
  position: relative;
  left: 50px;
  top: 50px;
}
</style>
