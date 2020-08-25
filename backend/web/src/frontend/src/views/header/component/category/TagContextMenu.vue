<template>
  <ContextMenu>
    <template slot="menuItems">
      <v-list-item v-for="(item, index) in items" :key="index" @click.prevent="item.action">
        <v-list-item-title>{{ item.title }}</v-list-item-title>
        <v-list-item-icon>
          <v-icon>
            {{ item.icon }}
          </v-icon>
        </v-list-item-icon>
      </v-list-item>
    </template>
  </ContextMenu>
</template>

<script>
import ContextMenu from '@/views/header/component/category/ContextMenu.vue';

export default {
  name: 'TagContextMenu',
  components: {
    ContextMenu,
  },
  props: {
    tag: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      items: [
        { title: '태그이름 수정', icon: 'mdi-pencil', action: '' },
        { title: '카테고리 변경', icon: 'mdi-pencil', action: '' },
        { title: '태그 삭제', icon: 'mdi-delete', action: '' },
      ],
    };
  },
};
</script>

<style scoped></style>
