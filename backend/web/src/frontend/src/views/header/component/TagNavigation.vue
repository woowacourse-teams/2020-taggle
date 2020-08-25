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
      <v-list-group sub-group class="text-left" value="true">
        <template v-slot:activator>
          <v-list-item-title class="font-weight-black text-h7">
            {{ title }}
          </v-list-item-title>
          <v-list-item-action v-if="id">
            <CategoryContextMenu :category="{ id, title }" />
          </v-list-item-action>
        </template>
        <v-list-item v-for="{ id, name } in tags" :key="id" :to="{ name: 'bookmarks', params: { id } }">
          <!--            <v-list-item-title @click.prevent="">-->
          <!--              <v-text-field :value="name" />-->
          <!--            </v-list-item-title>-->
          <v-list-item-title>
            {{ name }}
          </v-list-item-title>
          <v-list-item-action>
            <TagContextMenu :tag="{ id, title }" />
          </v-list-item-action>
        </v-list-item>
      </v-list-group>
    </v-list>

    <!-- category modify buttons -->
    <template v-slot:append>
      <v-divider></v-divider>
      <v-row width="100%">
        <v-col class="text-right">
          <CategoryAddModal />
        </v-col>
      </v-row>
    </template>
  </v-navigation-drawer>
</template>

<script>
import CategoryAddModal from '@/views/header/component/category/CategoryAddModal.vue';
import { FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { mapActions, mapGetters } from 'vuex';
import { CATEGORIES } from '@/store/share/getterTypes.js';
import CategoryContextMenu from '@/views/header/component/category/CategoryContextMenu.vue';
import TagContextMenu from '@/views/header/component/category/TagContextMenu.vue';

export default {
  name: 'TagNavigation',
  components: {
    CategoryContextMenu,
    TagContextMenu,
    CategoryAddModal,
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
