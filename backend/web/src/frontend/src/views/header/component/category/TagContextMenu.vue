<template>
  <ContextMenu>
    <template slot="menuItems">
      <v-list-item v-for="(item, index) in items" :key="index" @click.prevent="">
        <v-list-item-title>{{ item.title }}</v-list-item-title>
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
  data() {
    return {
      items: [{ title: '태그수정' }, { title: '태그삭제' }],
    };
  },
};
</script>

<style scoped></style>
