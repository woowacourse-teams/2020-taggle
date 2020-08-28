<template>
  <v-row>
    <v-col>
      <CategoryAddModal />
    </v-col>
  </v-row>
</template>

<script>
import CategoryAddModal from '@/views/header/component/category/CategoryAddModal.vue';

export default {
  name: 'TagNavigationFooter',
  components: {
    CategoryAddModal,
  },
};
</script>
