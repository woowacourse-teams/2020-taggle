<template>
  <v-list-item @click="move">
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
  methods: {
    move() {
      this.$router.push({name: 'bookmarks', query: { tag: this.subItem.id }});
    }
  },
};
</script>
