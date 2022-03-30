<template>
  <div>
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="5000"
      controls
      indicators
      background="#ababab"
      style="text-shadow: 1px 1px 2px #333; width: 750px; height: 560px"
    >
      <b-carousel-slide
        v-for="news in newsList"
        :key="news.link"
        style="text-align: center; width: 750px; height: 560px"
        class="img-wrapper"
      >
        <template #img>
          <img
            :src="news.thumbnail"
            alt="image slot"
            style="width: auto; height: 560px"
            @click="goto(news.link)"
          />
        </template>
        <h2
          style="
            background: #6565b6d0;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          "
        >
          {{ news.title }}
        </h2>
      </b-carousel-slide>
    </b-carousel>
  </div>
</template>

<script>
// function test(txt) {
//   console.log(txt);
// }
export default {
  props: {
    newsList: Array,
  },
  methods: {
    goto(link) {
      window.open(link);
    },
  },
};
</script>

<style lang="scss" scoped></style>
