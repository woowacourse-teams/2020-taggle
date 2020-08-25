<template>
  <v-card flat="true">
    <v-list v-for="category in categories" :key="category.id" class="grow">
      <CategoryGroup :category="category" />
    </v-list>
  </v-card>
</template>

<script>
import { FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { mapActions, mapGetters } from 'vuex';
import { CATEGORIES } from '@/store/share/getterTypes.js';
import CategoryGroup from '@/views/header/component/CategoryGroup.vue';

export default {
  name: 'TagNavigationBody',
  components: {
    CategoryGroup,
  },
  computed: {
    ...mapGetters([CATEGORIES]),
  },
  created() {
    this[FETCH_CATEGORIES]();
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES]),
  },
};
</script>
