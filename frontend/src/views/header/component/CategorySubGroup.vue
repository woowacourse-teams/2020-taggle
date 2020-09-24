<template>
  <v-list-item @click="push">
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
    push() {
      this.$router.push({name: 'bookmarks', query: { tag: this.subItem.id }}).catch(() => {});
    }
  },
};
</script>
