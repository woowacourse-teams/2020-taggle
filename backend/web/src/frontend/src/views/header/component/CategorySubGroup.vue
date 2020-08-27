<template>
  <v-list-item :to="{ name: 'bookmarks', params: { id: subItem.id } }">
    <v-list-item-title>
      {{ subItem.name }}
    </v-list-item-title>
    <v-list-item-action>
      <TagContextMenu :tag="subItem" />
    </v-list-item-action>
  </v-list-item>
</template>

<script>
import TagContextMenu from '@/views/header/component/category/TagContextMenu.vue';

export default {
  name: 'CategorySubGroup',
  components: {
    TagContextMenu,
  },
  props: {
    subItem: {
      type: Object,
      required: true,
    },
  },
};
</script>
