<template>
  <v-row width="100%">
    <v-col class="text-right">
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

<style scoped></style>
