<template>
  <v-container class="text-center" fill-height style="height: calc(100vh - 58px);">
    <v-row align="center">
      <v-col>
        <v-img center class="ma-auto" max-height="512px" max-width="512px" src="@/assets/images/tag_512x512.png" />
        <h1 class="display-2 primary--text">Whoops, 404</h1>

        <p>The page you were looking for does not exist</p>

        <v-btn :to="'/'" color="primary" outlined>
          Get me out of here!
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'ErrorPage',
};
</script>
