<template>
  <v-list-group sub-group class="text-left" value="true">
    <template v-slot:activator>
      <v-list-item-title class="font-weight-black text-h7">
        {{ category.title }}
      </v-list-item-title>
      <v-list-item-action v-if="category.id">
        <CategoryContextMenu :category="category" />
      </v-list-item-action>
    </template>
    <v-list-item v-for="{ id, name } in category.tags" :key="id" :to="{ name: 'bookmarks', params: { id } }">
      <!--            <v-list-item-title @click.prevent="">-->
      <!--              <v-text-field :value="name" />-->
      <!--            </v-list-item-title>-->
      <v-list-item-title>
        {{ name }}
      </v-list-item-title>
      <v-list-item-action>
        <TagContextMenu :tag="{ id }" />
      </v-list-item-action>
    </v-list-item>
  </v-list-group>
</template>

<script>
import CategoryContextMenu from '@/views/header/component/category/CategoryContextMenu.vue';
import TagContextMenu from '@/views/header/component/category/TagContextMenu.vue';

export default {
  name: 'CategoryGroup',
  components: {
    CategoryContextMenu,
    TagContextMenu,
  },
  props: {
    category: {
      type: Object,
      required: true,
    },
  },
};
</script>
