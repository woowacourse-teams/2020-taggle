<template>
  <v-card class="header-container" height="100%" width="306">
    <v-navigation-drawer permanent width="100%">
      <v-row class="fill-height" no-gutters>
        <v-navigation-drawer dark mini-variant mini-variant-width="56" permanent>
          <v-list-item class="px-2">
            <v-list-item-avatar>
              <v-img src="@/assets/images/TaggleLog.png"></v-img>
            </v-list-item-avatar>
          </v-list-item>
          <v-divider></v-divider>
          <v-list dense nav>
            <v-list-item v-for="{ icon, title } in icons" :key="title" link>
              <v-list-item-action>
                <v-icon>{{ icon }}</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>{{ title }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-divider />
            <BookmarkAddModal />
          </v-list>
          <template v-slot:append>
            <div class="text-center">
              <v-avatar>
                <img
                  src="https://avatars3.githubusercontent.com/u/45934117?s=460&u=32a2980278056093510eface6c7ddfca6bcd8bc9&v=4"
                  alt="evan"
                />
              </v-avatar>
            </div>
          </template>
        </v-navigation-drawer>

        <v-col>
          <v-navigation-drawer permanent width="100%">
            <v-text-field
              prepend-inner-icon="mdi-magnify"
              placeholder="Tag Search"
              filled
              rounded
              dense
              class="ma-2"
              height="5%"
            ></v-text-field>
            <v-card tile width="250" height="95%">
              <v-list v-for="{ id, title, tags } in categories" :key="id" class="grow">
                <v-list-group class="text-left mt-2" link value="true">
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
            </v-card>
            <template v-slot:append>
              <v-row width="100%">
                <v-col class="text-right">
                  <CategoryAddModal></CategoryAddModal>
                  <CategoryTagModifyModal></CategoryTagModifyModal>
                </v-col>
              </v-row>
            </template>
          </v-navigation-drawer>
        </v-col>
      </v-row>
    </v-navigation-drawer>
  </v-card>
</template>

<script>
import CategoryAddModal from '@/views/header/component/CategoryAddModal.vue';
import CategoryTagModifyModal from '@/views/header/component/TagEditModal.vue';
import BookmarkAddModal from '@/views/header/component/BookmarkAddModal.vue';
import { FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { CATEGORIES } from '@/store/share/getterTypes.js';
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'BookmarkHeader',
  components: {
    CategoryAddModal,
    CategoryTagModifyModal,
    BookmarkAddModal,
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
  data() {
    return {
      icons: [{ icon: 'local_offer', title: 'tag' }],
    };
  },
};
</script>

<style>
.header-container {
  position: fixed !important;
}
</style>
