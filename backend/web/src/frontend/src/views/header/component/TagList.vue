<template>
  <v-navigation-drawer permanent>
    <!-- tag search bar -->
    <v-text-field
      prepend-inner-icon="mdi-magnify"
      placeholder="Tag Search"
      filled
      rounded
      dense
      class="ma-2"
      hide-details="true"
    ></v-text-field>

    <!-- tag & category list-->
    <v-list v-for="{ id, title, tags } in categories" :key="id" class="grow">
      <v-list-group class="text-left" link value="true">
        <template v-slot:activator>
          <v-list-item-content>
            <v-list-item-title class="font-weight-black text-h7">
              {{ title }}
            </v-list-item-title>
          </v-list-item-content>
        </template>
        <v-list-item v-for="{ id, name } in tags" :key="id" :to="{ name: 'bookmarks', params: { id } }">
          <v-list-item-content>
            {{ name }}
          </v-list-item-content>
        </v-list-item>
      </v-list-group>
    </v-list>

    <!-- category modify buttons -->
    <template v-slot:append>
      <v-divider></v-divider>
      <v-row width="100%">
        <v-col class="text-right">
          <CategoryAddModal />
          <CategoryTagModifyModal />
        </v-col>
      </v-row>
    </template>
  </v-navigation-drawer>
</template>

<script>
import CategoryAddModal from '@/views/header/component/CategoryAddModal.vue';
import CategoryTagModifyModal from '@/views/header/component/TagEditModal.vue';
import { FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { mapActions, mapGetters } from 'vuex';
import { CATEGORIES } from '@/store/share/getterTypes.js';

export default {
  name: 'tagList',
  components: {
    CategoryAddModal,
    CategoryTagModifyModal,
  },
  created() {
    this[FETCH_CATEGORIES]();
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES]),
  },
  computed: {
    ...mapGetters([CATEGORIES]),
  },
};
</script>

<style scoped></style>
